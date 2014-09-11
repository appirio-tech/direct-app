/*
 * Copyright (C) 2007, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.failuretests;

import java.io.File;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.converter.ConverterUtility;

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
 * @author iRabbit
 * @version 1.1
 */
public class ConverterUtilityFailureTests extends TestCase {

    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = FailureTestHelper.getPath("config" + File.separator
            + "Project_Phase_Template_Config.xml");

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
    private static final String BAD_CONFIG_FILE = FailureTestHelper.getPath("bad_config" + File.separator
            + "Xml_Phase_Template_Persistence_Bad_Config.xml");

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
    protected void setUp() throws Exception {
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
    protected void tearDown() throws Exception {
        Helper.clearConfig();
        FailureTestHelper.clearDB();
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
    public void testMain_Failure1() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
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
    public void testMain_Failure2() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-i", NEW_XML_NAMESPACE, "-o", "no name space"});
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
    public void testMain_Failure3() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-i", "nospace", "-o", DB_NAMESPACE});
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
    public void testMain_Failure4() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-i", NEW_XML_NAMESPACE, "-o", "ConnectionException"});
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
    public void testMain_Failure5() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-i", NEW_XML_NAMESPACE, "-o", "SQLException"});
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
    public void testMain_Failure6() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-i", "nonexist_filename", "-o", DB_NAMESPACE});
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
    public void testMain_Failure7() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-i", "malformed_file", "-o", DB_NAMESPACE});
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
    public void testMain_Failure8() throws Exception {
        FailureTestHelper.initializeDBWithNoEntry();
        ConverterUtility.main(new String[] {"-i", "empty_template_name", "-o", DB_NAMESPACE});
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
    public void testMain_Failure9() throws Exception {
        FailureTestHelper.initializeDB();
        ConverterUtility.main(new String[] {"-i", "invalid_date", "-o", "DefaultConnection", "-auto"});
    }

}
