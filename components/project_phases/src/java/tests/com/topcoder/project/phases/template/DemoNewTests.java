/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.project.phases.template.converter.ConverterUtility;
import com.topcoder.project.phases.template.persistence.DBPhaseTemplatePersistence;
import com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * The unit test class used for this component demonstration on its new functionality in version 1.1 .
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class DemoNewTests extends TestCase {

    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "test_files/config/Project_Phase_Template_Config.xml";

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        // load the ConfigurationObject from the input file
        Helper.loadConfig(CONFIG_FILE);
        TestHelper.initializeDB();

    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearDB();
        Helper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DemoNewTests.class);
    }

    /**
     * <p>
     * Tests for the demo usage of DBPhaseTemplatePersistence and DefaultPhaseTemplate's new method.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDBPhaseTemplatePersistenceDemo() throws Exception {

        // 1. Create the PhaseTemplate instance with use of specific
        // DBPhaseTemplatePersistence and StartDateGenerator

        //  create DBPhaseTemplatePersistence instance
        PhaseTemplatePersistence persistence = new DBPhaseTemplatePersistence("com.topcoder.project.phases.template."
            + "persistence.DBPhaseTemplatePersistence");
        // create StartDateGenerator instance
        StartDateGenerator startDateGenerator = new RelativeWeekTimeStartDateGenerator(
            "com.topcoder.project.phases.template." + "startdategenerator.RelativeWeekTimeStartDateGenerator");
        // create PhaseTemplate instance with the persistence and startDateGenerator
        DefaultPhaseTemplate template =
            new DefaultPhaseTemplate(persistence, startDateGenerator, new DefaultWorkdays());

        // 2 Retrieve names of all available templates from specific category
        // retrieve names of all available templates from category 2
        String[] templateNames = template.getAllTemplateNames(2);
        for (int i = 0; i < templateNames.length; ++i) {
            System.out.println(templateNames[i]);
        }

        // 3 Retrieve the name of the default template for specific category
        // retrieve the name of the default template for category 2
        String templateName = template.getDefaultTemplateName(2);
        System.out.println(templateName);

        // 4 Retrieve template information
        // retrieve the category of the template with name "New_Design"
        int category = template.getTemplateCategory("New_Design");
        System.out.println("category: " + category);
        // retrive the description of the template with name "New_Design"
        String description = template.getTemplateDescription("New_Design");
        System.out.println("description: " + description);
        // retrive the creation date of the template with name "New_Design"
        Date creationDate = template.getTemplateCreationDate("New_Design");
        System.out.println("creation date: " + creationDate);

        // 5 Delete a template from database
        // create DBPhaseTemplatePersistence instance
        DBPhaseTemplatePersistence dbPersistence = new DBPhaseTemplatePersistence(
             "com.topcoder.project.phases.template." + "persistence.DBPhaseTemplatePersistence");
        // delete the template with name "New_Design"
        dbPersistence.removeTemplate("New_Design");
    }

    /**
     * <p>
     * Tests for the demo usage of migration utility ConverterUtility.
     * </p>
     */
    public void testUtilityDemo() {
        ConverterUtility.main(new String[] {"-i",
            "com.topcoder.project.phases.template.persistence.DemoXmlPhaseTemplatePersistence"});
    }
}
