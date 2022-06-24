/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.converter;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.TestHelper;
import com.topcoder.project.phases.template.persistence.DBPhaseTemplatePersistence;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests on class <code>ConverterUtility</code>.
 * </p>
 *
 * <p>
 * It test its main method for accuracy.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class ConverterUtilityTests extends TestCase {

    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "test_files" + File.separator + "config" + File.separator
        + "Project_Phase_Template_Config.xml";

    /**
     * <p>
     * Represent the new namespace for xml persistence.
     * </p>
     */
    private static final String NEW_XML_NAMESPACE = "com.topcoder.project.phases.template."
        + "persistence.XmlPhaseTemplatePersistenceNew";

    /**
     * <p>
     * Bad configuration file.
     * </p>
     */
    private static final String BAD_CONFIG_FILE = "test_files" + File.separator + "bad_config" + File.separator
        + "Xml_Phase_Template_Persistence_Bad_Config.xml";

    /**
     * <p>
     * Represent the namespace for database persistence.
     * </p>
     */
    private static final String DB_NAMESPACE = "com.topcoder.project.phases.template."
        + "persistence.DBPhaseTemplatePersistence";

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        Helper.loadConfig(CONFIG_FILE);
        Helper.loadConfig(BAD_CONFIG_FILE);

    }

    /**
     * <p>
     * Clear the environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        Helper.clearConfig();
        TestHelper.clearDB();
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test with "-auto" enabled. It use DBPhaseTemplatePersistence to generate the phases from
     * the database to verify that the entries are inserted correctly. And it also read some other
     * field of the template to verify the results are correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainAutoEnabled() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        // call main method
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", DB_NAMESPACE, "-auto"});

        // verify by using DBPhaseTemplatePersistence to generate the phases

        // create needed objects
        Date startDate = Calendar.getInstance().getTime();
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(startDate, workdays);

        DBPhaseTemplatePersistence persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(),
            "test");

        persistence.generatePhases("New_Design", project);
        // check the generated phases
        Helper.checkSamplePhases(project.getAllPhases());
        // check the phase id
        Phase[] phases = project.getAllPhases();
        for (int i = 0; i < phases.length; ++i) {
            assertEquals("Phase id incorrect.", phases[i].getPhaseType().getId(), phases[i].getId());
        }

        // verify the creation date

        // get the date
        Date date = persistence.getTemplateCreationDate("New_Development");

        // verify the results
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals("Date Incorrect", 2007, calendar.get(calendar.YEAR));
        assertEquals("Date Incorrect", 11, calendar.get(calendar.MONTH));
        assertEquals("Date Incorrect", 4, calendar.get(calendar.DAY_OF_MONTH));
        assertEquals("Date Incorrect", 14, calendar.get(calendar.HOUR_OF_DAY));
        assertEquals("Date Incorrect", 45, calendar.get(calendar.MINUTE));
        assertEquals("Date Incorrect", 2, calendar.get(calendar.SECOND));

        // test with null date
        assertNull("Should get null value", persistence.getTemplateCreationDate("Development"));

        // verify the descrption

        assertEquals("Description Incorrect", "This is a design phases template", persistence
            .getTemplateDescription("New_Design"));
        assertEquals("Description Incorrect", "This is a development phases template", persistence
            .getTemplateDescription("New_Development"));
        assertNull("Description Incorrect", persistence.getTemplateDescription("Development"));
        assertNull("Description Incorrect", persistence.getTemplateDescription("Design"));
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test with "-auto" disabled. It verifies that no sql insert statement is executed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainAutoDisabled() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", DB_NAMESPACE});

        // verify using the DBPhaseTemplatePersistence
        // create needed objects
        Date startDate = Calendar.getInstance().getTime();
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(startDate, workdays);

        DBPhaseTemplatePersistence persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(),
            "test");
        try {
            persistence.generatePhases("New_Design", project);
            fail("No entries should be added into the database.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test with "-o" disabled, and verify it could work without cnnecting to the database.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainOutputDisabled() throws Exception {
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when command is not correct, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainCommandIncorrect() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-dind", NEW_XML_NAMESPACE});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when the input database namespace is unknown, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainCommandDBNamespaceUnknown() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", "no name space"});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when the input xml namespace is unknown, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainCommandXmlNamespaceUnknown() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", "nospace", "-onamespace", DB_NAMESPACE});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when it fail to connect to the database, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainBadConnection() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", "ConnectionException"});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when it fail to connect to the database, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainSqlException() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", "SQLException"});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when it fail to connect to the database, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainXmlFileNotExist() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", "nonexist_filename", "-onamespace", DB_NAMESPACE});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when the xml file is malformed, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainXmlFileMalformed() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", "malformed_file", "-onamespace", DB_NAMESPACE});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It test when the xml file is malformed, corresponding message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainEmptyTemplateName() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", "empty_template_name", "-onamespace", DB_NAMESPACE});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It calls the main method when there already entries in the database. No error message should
     * show. It also use the default connection name.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainWithOldEntries() throws Exception {
        TestHelper.initializeDB();
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", "DefaultConnection"});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It tests when it fail to execute the insert command, corresponding error message would show.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainExecuteInsertFail() throws Exception {
        TestHelper.initializeDB();
        ConverterUtility.main(new String[] {"-inamespace", "invalid_date", "-onamespace", "DefaultConnection"
            , "-auto"});
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It tests with default template for category 1, and use db persistence to verify the result.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainDefault() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-inamespace", "ConverterUtilityTests", "-onamespace"
            , "DefaultConnection", "-auto"});
        DBPhaseTemplatePersistence persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(),
            "test");
        assertEquals("The default template name is not correct.", "New_Design_Default", persistence
            .getDefaultTemplateName(1));
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * This test case is almost the same as the case testMainAutoEnabled, execept that "-ifile" argument
     * is used here. The configuration manager should load the config from the given file automatically.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainAutoEnabledWithAdditonalConfigFile() throws Exception {
        TestHelper.initializeDBWithNoEntry();
        // clear the config
        Helper.clearConfig();

        // call main method
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", DB_NAMESPACE,
            "-ifile", CONFIG_FILE, "-auto"});

        // verify by using DBPhaseTemplatePersistence to generate the phases

        // create needed objects
        Date startDate = Calendar.getInstance().getTime();
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(startDate, workdays);

        DBPhaseTemplatePersistence persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(),
            "test");

        persistence.generatePhases("New_Design", project);
        // check the generated phases
        Helper.checkSamplePhases(project.getAllPhases());
        // check the phase id
        Phase[] phases = project.getAllPhases();
        for (int i = 0; i < phases.length; ++i) {
            assertEquals("Phase id incorrect.", phases[i].getPhaseType().getId(), phases[i].getId());
        }

        // verify the creation date

        // get the date
        Date date = persistence.getTemplateCreationDate("New_Development");

        // verify the results
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals("Date Incorrect", 2007, calendar.get(calendar.YEAR));
        assertEquals("Date Incorrect", 11, calendar.get(calendar.MONTH));
        assertEquals("Date Incorrect", 4, calendar.get(calendar.DAY_OF_MONTH));
        assertEquals("Date Incorrect", 14, calendar.get(calendar.HOUR_OF_DAY));
        assertEquals("Date Incorrect", 45, calendar.get(calendar.MINUTE));
        assertEquals("Date Incorrect", 2, calendar.get(calendar.SECOND));

        // test with null date
        assertNull("Should get null value", persistence.getTemplateCreationDate("Development"));

        // verify the descrption

        assertEquals("Description Incorrect", "This is a design phases template", persistence
            .getTemplateDescription("New_Design"));
        assertEquals("Description Incorrect", "This is a development phases template", persistence
            .getTemplateDescription("New_Development"));
        assertNull("Description Incorrect", persistence.getTemplateDescription("Development"));
        assertNull("Description Incorrect", persistence.getTemplateDescription("Design"));
    }

    /**
     * <p>
     * Test the method main(args[]) for accuracy.
     * </p>
     *
     * <p>
     * It tests when "-ifile" specify a invalid file name and proper error message should be shown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMainAutoEnabledWithAdditonalConfigFile11() throws Exception {
        TestHelper.initializeDBWithNoEntry();

        // call main method
        ConverterUtility.main(new String[] {"-inamespace", NEW_XML_NAMESPACE, "-onamespace", DB_NAMESPACE,
            "-ifile", "nofile", "-auto"});
    }
}
